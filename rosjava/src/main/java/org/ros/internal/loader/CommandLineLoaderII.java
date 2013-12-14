package org.ros.internal.loader;

import java.util.List;
import java.util.Map;

import org.ros.namespace.GraphName;
import org.ros.node.NodeConfiguration;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * Compared to the super class, this improvement reads private command line parameters, 
 * copies them into the privateRemaps map and stores them in the nodeConfiguration.
 * Modified class org.ros.internal.node.DefaulNode sends these parameters to the parameter
 * server under nodes name.   
 * 
 * ///my
 * @author Jaroslav Vitku
 *
 */
public class CommandLineLoaderII extends org.ros.internal.loader.CommandLineLoader{

	protected final Map<String, String> privateRemaps;


	public CommandLineLoaderII(List<String> argv) {
		this(argv, System.getenv());
	}

	public CommandLineLoaderII(List<String> argv, Map<String, String> env) {
		super(argv, env);

		privateRemaps = Maps.newHashMap(); 
	}


	@Override
	protected void parseRemappingArguments() {
		for (String remapping : remappingArguments) {
			Preconditions.checkState(remapping.contains(":="));
			String[] remap = remapping.split(":=");
			if (remap.length > 2) {
				throw new IllegalArgumentException("Invalid remapping argument: " + remapping);
			}
			if (remapping.startsWith("__")) {
				specialRemappings.put(remap[0], remap[1]);
			}else if(remapping.startsWith("_")) {
				privateRemaps.put(remap[0], remap[1]);
			} else {
				remappings.put(GraphName.of(remap[0]), GraphName.of(remap[1]));
			}
		}
	}


	/**
	 * Create NodeConfiguration according to ROS command-line and environment
	 * specification.
	 * 
	 * Here, if some parameters are passed, start the ROS node and set them on the server.
	 */
	public NodeConfiguration build() {
		NodeConfiguration nodeConfiguration = super.build();

		if(privateRemaps.isEmpty())
			return nodeConfiguration;

		nodeConfiguration.setPrivateRemappings(privateRemaps);

		return nodeConfiguration;
	}

}
